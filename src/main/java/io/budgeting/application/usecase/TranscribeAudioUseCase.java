package io.budgeting.application.usecase;

import io.budgeting.application.port.AudioTranscriptionGateway;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;

@Service
public class TranscribeAudioUseCase {

    private static final String SYSTEM_PROMPT = """
            Voce e um assistente financeiro. O usuario enviou um comando por voz que foi transcrito.
            Interprete a intencao (registrar transacao, consultar gastos, resumo, etc.) e responda
            de forma clara e objetiva em portugues do Brasil.
            """;

    private final AudioTranscriptionGateway transcriptionGateway;
    private final ChatClient chatClient;

    public TranscribeAudioUseCase(AudioTranscriptionGateway transcriptionGateway,
                                  ChatClient.Builder chatClientBuilder) {
        this.transcriptionGateway = transcriptionGateway;
        this.chatClient = chatClientBuilder.build();
    }

    public String execute(MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao ler arquivo de audio", e);
        }
        String transcript = transcriptionGateway.transcribe(bytes, file.getOriginalFilename());
        return chatClient
                .prompt()
                .system(SYSTEM_PROMPT)
                .user(transcript)
                .call()
                .content();
    }
}
