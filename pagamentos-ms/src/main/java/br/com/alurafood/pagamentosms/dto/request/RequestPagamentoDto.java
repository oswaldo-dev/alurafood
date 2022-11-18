package br.com.alurafood.pagamentosms.dto.request;

import br.com.alurafood.pagamentosms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class RequestPagamentoDto {

    @NotNull
    @Positive
    private BigDecimal valor;
    @NotBlank
    @Size(max = 100)
    private String nome;
    @NotBlank
    @Size(max = 19)
    private String numero;
    @NotBlank
    @Size(max = 7)
    private String expiracao;
    @NotBlank
    @Size(min = 3, max = 3)
    private String codigo;
    private Status status;
    @NotNull
    private Long pedidoId;
    @NotNull
    private Long formaDePagamentoId;

}
