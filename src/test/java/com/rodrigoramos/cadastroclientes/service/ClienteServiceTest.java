package com.rodrigoramos.cadastroclientes.service;

import com.rodrigoramos.cadastroclientes.model.Address;
import com.rodrigoramos.cadastroclientes.model.Cliente;
import com.rodrigoramos.cadastroclientes.repository.ClienteRepository;
import com.rodrigoramos.cadastroclientes.service.exceptions.ObjectNotFoundException;
import com.rodrigoramos.cadastroclientes.service.impl.ClienteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSavedClienteSuccessFully() {

        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(null, "Pedro da Silva", "123123", "M",
                LocalDate.of(1960, Month.JANUARY, 1), 88, address);

//        given(clienteRepository.findClienteByNomeCompleto(cliente.getNomeCompleto())).willReturn(Optional.empty());
        given(clienteRepository.save(cliente)).willAnswer(invocation -> invocation.getArgument(0));

        Cliente savedCliente = clienteService.save(cliente);

        assertThat(savedCliente).isNotNull();

        verify(clienteRepository).save(any(Cliente.class));

    }

/*    @Test
    public void shouldThrowErrorWhenSaveUserWithExistingEmail() {
        final Cidade cidade = new Cidade(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(1L, "Pedro da Silva", "123123", "M",
                LocalDate.of(1960, Month.JANUARY, 1), 88, cidade);

        given(clienteRepository.findClienteByCpf(cliente.getCpf())).willReturn(Optional.of(cliente));

        assertThrows(ClienteRegistrationException.class, () -> clienteService.save(cliente));

        verify(clienteRepository, never()).save(any(Cliente.class));
    }*/


    @Test
    public void shouldReturnFindAll() {
        List<Cliente> datas = new ArrayList();
        final Address address = new Address(null, "Lajeado", "RS");
        datas.add(new Cliente(1L, "Pedro da Silva", "1231", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address));
        datas.add(new Cliente(2L, "Pedro da Silva", "12312", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address));
        datas.add(new Cliente(3L, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address));

        given(clienteRepository.findAll()).willReturn(datas);

        List<Cliente> expected = clienteService.findAll();

        assertEquals(expected, datas);
    }

    @Test
    public void findClienteById() {
        final Long id = 1L;
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(1L, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        given(clienteRepository.findById(id)).willReturn(Optional.of(cliente));

        final Cliente expected = clienteService.findById(id);

        assertThat(expected).isNotNull();
    }

    @Test
    public void findClienteByNomeCompleto() {
        final Long id = 1L;
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(1L, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        given(clienteRepository.findClienteByNomeCompleto(cliente.getNomeCompleto())).willReturn(Optional.of(cliente));

        final Cliente expected = clienteService.findByNomeCompleto(cliente.getNomeCompleto());

        assertThat(expected).isNotNull();
    }

    @Test
    public void shouldThrowErrorWhenFindClienteByNomeCompleto() {
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(null, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        // given(clienteRepository.findById(id)).willReturn(Optional.of(cliente));

        assertThrows(ObjectNotFoundException.class, () -> clienteService.findByNomeCompleto("Maria da Silva Silva"));

        verify(clienteRepository, never()).findClienteByNomeCompleto("Marsadas");
    }

    @Test
    public void shouldThrowErrorWhenFindClienteById() {
        final Long id = 1L;
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(1L, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        // given(clienteRepository.findById(id)).willReturn(Optional.of(cliente));

        assertThrows(ObjectNotFoundException.class, () -> clienteService.findById(2L));

        verify(clienteRepository, never()).findById(22L);
    }


    @Test
    public void shouldBeDeleted() {
        final Long clientId = 1L;
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(1L, "Pedro da Silva", "1", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        clienteService.deleteById(clientId);
        clienteService.deleteById(clientId);

        verify(clienteRepository, times(2)).deleteById(clientId);
    }

    @Test
    public void shouldThrowErrorWhenBeDeleted() {
        final Long id = 1L;
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(1L, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        // given(clienteRepository.findById(id)).willReturn(Optional.of(cliente));

        assertThrows(ObjectNotFoundException.class, () -> clienteService.deleteById(2L));

        verify(clienteRepository, never()).deleteById(22L);
    }

    @Test
    public void findClienteByCpf() {
        final Long id = 1L;
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(1L, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        given(clienteRepository.findClienteByCpf(cliente.getCpf())).willReturn(Optional.of(cliente));

        final Cliente expected = clienteService.findClienteByCpf(cliente.getCpf());

        assertThat(expected).isNotNull();
    }

    @Test
    public void shouldThrowErrorWhenFindClienteByCpf() {
        final Address address = new Address(null, "Lajeado", "RS");
        final Cliente cliente = new Cliente(null, "Pedro da Silva", "123123", "M", LocalDate.of(1960, Month.JANUARY, 1), 88, address);

        // given(clienteRepository.findById(id)).willReturn(Optional.of(cliente));

        assertThrows(ObjectNotFoundException.class, () -> clienteService.findClienteByCpf("123124424"));

        verify(clienteRepository, never()).findClienteByCpf("23131231fd");
    }


}
