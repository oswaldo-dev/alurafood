package br.com.alurafood.pagamentosms.controller;

import br.com.alurafood.pagamentosms.dto.request.RequestPagamentoDto;
import br.com.alurafood.pagamentosms.dto.response.ResponsePagamentoDto;
import br.com.alurafood.pagamentosms.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<Page<ResponsePagamentoDto>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<ResponsePagamentoDto> pagamentos = pagamentoService.getAll(pageable);
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePagamentoDto> getById(@PathVariable Long id) {
        ResponsePagamentoDto pagamento = pagamentoService.getById(id);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    public ResponseEntity<ResponsePagamentoDto> post(@RequestBody @Valid RequestPagamentoDto pagamentoDto) {
        ResponsePagamentoDto pagamento = pagamentoService.post(pagamentoDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(uri).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePagamentoDto> update(@PathVariable Long id, @RequestBody @Valid RequestPagamentoDto pagamentoDto) {
        ResponsePagamentoDto pagamento = pagamentoService.update(id, pagamentoDto);
        return ResponseEntity.ok(pagamento);
    }
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarPagamento(@PathVariable Long id) {
        pagamentoService.confirmarPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
