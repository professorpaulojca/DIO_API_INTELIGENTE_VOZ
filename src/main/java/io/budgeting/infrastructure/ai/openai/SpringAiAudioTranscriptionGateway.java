package io.budgeting.infrastructure.ai.openai;

import io.budgeting.application.port.AudioTranscriptionGateway;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class SpringAiAudioTranscriptionGateway implements AudioTranscriptionGateway {

    private final OpenAiAudioTranscriptionModel transcriptionModel;

    public SpringAiAudioTranscriptionGateway(OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    @Override
    public String transcribe(byte[] audio, String filename) {
        ByteArrayResource resource = new ByteArrayResource(audio) {
            @Override
            public String getFilename() {
                return filename != null ? filename : "audio.mp3";
            }
        };
        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(resource);
        return transcriptionModel.call(prompt).getResult().getOutput();
    }
}
