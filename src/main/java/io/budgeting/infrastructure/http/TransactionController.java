package io.budgeting.infrastructure.http;

import io.budgeting.application.usecase.ListTransactionsByCategoryUseCase;
import io.budgeting.application.usecase.PersistTransactionUseCase;
import io.budgeting.application.usecase.TranscribeAudioUseCase;
import io.budgeting.infrastructure.http.request.TransactionRequest;
import io.budgeting.infrastructure.http.response.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase;
    private final TranscribeAudioUseCase transcribeAudioUseCase;

    public TransactionController(
            PersistTransactionUseCase persistTransactionUseCase,
            ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase,
            TranscribeAudioUseCase transcribeAudioUseCase
    ) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionsByCategoryUseCase = listTransactionsByCategoryUseCase;
        this.transcribeAudioUseCase = transcribeAudioUseCase;
    }

    @PostMapping
    public TransactionResponse create(@RequestBody @Valid TransactionRequest request) {
        return persistTransactionUseCase.execute(request);
    }

    @GetMapping("/{category}")
    public List<TransactionResponse> readTransactions(@PathVariable String category) {
        return listTransactionsByCategoryUseCase.execute(category);
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String transcribe(@RequestParam("file") MultipartFile file) {
        return transcribeAudioUseCase.execute(file);
    }
}
