package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.request.RequestPagamentoDto;
import br.com.alurafood.pagamentos.dto.response.ResponsePagamentoDto;
import br.com.alurafood.pagamentos.enums.Status;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
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
}
