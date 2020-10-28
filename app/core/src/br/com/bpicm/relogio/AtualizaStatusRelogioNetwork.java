package br.com.bpicm.relogio;

import br.com.bpicm.relogio.dto.RelogioStatusDTO;
import br.com.bpicm.relogio.model.PiscarLedModoEnum;
import br.com.bpicm.relogio.model.StatusConexaoEnum;
import br.com.bpicm.relogio.model.StatusRelogio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

import java.io.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtualizaStatusRelogioNetwork implements Runnable, Disposable {

    private static final String LOG_TAG = "AtualizaStatusRelogio";
    private static final String TAG_TERMINAL = "terminal";
    private final br.com.bpicm.relogio.model.StatusRelogio statusRelogio;
    private final String host;
    private final Pattern horaPattern;
    private BufferedReader socketReader;
    private Socket socket;
    private final StringBuffer commandToSend = new StringBuffer();


    public AtualizaStatusRelogioNetwork(StatusRelogio statusRelogio, String host) {
        this.statusRelogio = statusRelogio;
        this.host = host;
        this.horaPattern = Pattern.compile("(\\d+):(\\d+)");
    }

    /*
     ptrTimer
     newInterval=60000
     L-dif GMIN:-1
     cw
     M
     M
     Minuto.
     D-dif GMIN:0
     9:54
     */

    @Override
    public void run() {
        try {
            indicarNaoConectado();
            while (true) {
                try {
                    if (socket!=null && socket.isConnected()) {
                        extrairStatusDasMensagens();
                    } else {
                        conectar();
                    }
                } catch (IOException ex) {
                    // problema na leitura do Stream
                    Gdx.app.error(LOG_TAG, "sss", ex);
                    indicarNaoConectado();
                } catch (GdxRuntimeException ex) {
                    // socket não pode ser conectado
                    Gdx.app.error(LOG_TAG, "qqq", ex);
                    indicarNaoConectado();
                }
            } // loop
        } catch (InterruptedException ex) {
            // applicação finalizada
            Gdx.app.error(LOG_TAG, "rrr", ex);
            this.statusRelogio.setStatusConexao(StatusConexaoEnum.NAO_CONECTADO);
            this.statusRelogio.setUltimaAtualizacaoMillis(System.currentTimeMillis());
        }
    }

    private void indicarNaoConectado() throws InterruptedException {
        if (socket!=null) {
            socket.dispose();
        }
        this.statusRelogio.setStatusConexao(StatusConexaoEnum.NAO_CONECTADO);
        this.statusRelogio.setUltimaAtualizacaoMillis(System.currentTimeMillis());
    }

    private void extrairStatusDasMensagens() throws IOException, InterruptedException {
        if (socketReader.ready()) {
            Gdx.app.debug(TAG_TERMINAL, "readline()");
            String linha;
            try {
                linha = socketReader.readLine();
            } catch (java.net.SocketTimeoutException ex) {
                linha = null;
            }
            if (linha==null || linha.isEmpty()) {
                Thread.sleep(1000);
            } else {
                Gdx.app.debug(TAG_TERMINAL, linha);

                if (linha.matches("^\\{.*\\}$")) {
                    lerStatus(linha);
                }
            }
        } else {
            if (commandToSend.length() > 0) {
                String command = commandToSend.toString();
                commandToSend.setLength(0); // limpa o StringBuffer
                Gdx.app.debug(TAG_TERMINAL, ">"+command);
                socket.getOutputStream().write(command.getBytes());
            }
        }
    }

    private void lerStatus(String linha) {
        Json json = new Json();
        br.com.bpicm.relogio.dto.RelogioStatusDTO status;
        try {
            status = json.fromJson(br.com.bpicm.relogio.dto.RelogioStatusDTO.class, linha);
        } catch (SerializationException ex) {
            status = null;
            Gdx.app.debug(LOG_TAG, "Falha ao decodificar JSON.", ex);
        }
        if (status!=null) {
            atualizaStatus(status);
        }
    }

    private void atualizaStatus(RelogioStatusDTO status) {
        Matcher horaMatcher = horaPattern.matcher(status.getPtr().getTime());
        if (horaMatcher.find()) {
            int hora = Integer.parseInt(horaMatcher.group(1));
            int minuto = Integer.parseInt(horaMatcher.group(2));
            statusRelogio.setHoraPonteiro(String.format(Locale.getDefault(), "%02d:%02d", hora, minuto));
        }
        statusRelogio.setHoraRtc(status.getRtc().getTime());
        statusRelogio.setUltimaAtualizacaoMillis(System.currentTimeMillis());
    }

    private void conectar() throws InterruptedException {
        // cria o socket
        this.statusRelogio.setStatusConexao(StatusConexaoEnum.CONECTANDO);
        this.statusRelogio.setUltimaAtualizacaoMillis(System.currentTimeMillis());

        SocketHints hints = new SocketHints();
        hints.connectTimeout = 60000; // 1min
        hints.socketTimeout = 1000; // 1segundo de block no read
        try {
            this.socket = Gdx.net.newClientSocket(Net.Protocol.TCP,
                    host,
                    23, hints);

            while (!socket.isConnected())
                wait(1000); // retenta a cada 1s.

            this.statusRelogio.setStatusConexao(StatusConexaoEnum.CONECTADO);
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.statusRelogio.setMensagem("Conectado!");
        } catch (GdxRuntimeException ex) {
            this.statusRelogio.setMensagem(ex.getMessage());
            this.statusRelogio.setStatusConexao(StatusConexaoEnum.CONECTANDO);
        }
        this.statusRelogio.setUltimaAtualizacaoMillis(System.currentTimeMillis());
    }

    private void sendComando(String s) {
        // stringBuffer é thread-safe.
        commandToSend.append(s);
        commandToSend.append("\r\n");
    }

    public void status() {
        sendComando("r.status();");
    }

    public void acendeLed() {
        sendComando("led.on();");
    }

    public void apagaLed() {
        sendComando("led.off();");
    }

    public void piscarLed(PiscarLedModoEnum modo) {
        sendComando(String.format(Locale.getDefault(), "led.piscar('%s');",modo.name().toLowerCase()));
    }

    public void incrementDifMinutos(int increment) {
        sendComando(String.format(Locale.getDefault(), "print(encoder.ptr.incrementDifMinutos(%d));",increment));
    }

    public void saveDifMinutos() {
        sendComando("encoder.ptr.saveDifMinutos();");
    }

    @Override
    public void dispose() {
        this.socket.dispose();
    }
}
