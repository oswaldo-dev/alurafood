package br.com.alurafood.pagamentosms.service;

import br.com.alurafood.pagamentosms.dto.request.RequestPagamentoDto;
import br.com.alurafood.pagamentosms.dto.response.ResponsePagamentoDto;
import br.com.alurafood.pagamentosms.enums.Status;
import br.com.alurafood.pagamentosms.http.PedidoClient;
import br.com.alurafood.pagamentosms.model.Pagamento;
import br.com.alurafood.pagamentosms.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ModelMapper modelMapper;
    private final PedidoClient pedidoClient;

    public Page<ResponsePagamentoDto> getAll(Pageable pageable) {
        return pagamentoRepository.findAll(pageable)
                .map(pagamento -> modelMapper.map(pagamento, ResponsePagamentoDto.class));
    }

    public ResponsePagamentoDto getById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, ResponsePagamentoDto.class);
    }

    public ResponsePagamentoDto post(RequestPagamentoDto pagamentoDto) {
        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, ResponsePagamentoDto.class);
    }

    public ResponsePagamentoDto update(Long id, RequestPagamentoDto pagamentoDto) {
        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
        pagamento.setId(id);
        Pagamento pagamentoSave = pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamentoSave, ResponsePagamentoDto.class);
    }

    public void delete(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        pagamentoRepository.delete(pagamento);
    }

    public void confirmarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        pagamento.setStatus(Status.CONFIRMADO);

        pagamentoRepository.save(pagamento);
        pedidoClient.atualizarPagamento(pagamento.getId());
    }
}
