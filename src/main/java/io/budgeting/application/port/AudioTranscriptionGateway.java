package io.budgeting.application.port;

public interface AudioTranscriptionGateway {

    String transcribe(byte[] audio, String filename);
}
