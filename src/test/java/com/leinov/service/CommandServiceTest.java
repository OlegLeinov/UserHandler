package com.leinov.service;

import com.leinov.entity.Command;
import com.leinov.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;

import static com.leinov.entity.Command.SupportedCommand.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CommandServiceTest {

    private CommandService commandService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ResultSet resultSet;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        commandService = new CommandService(userRepository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void verifyCommandServiceInvokesAddWhenAddCommand() {
        Command command = new Command(ADD, 1, "guid", "name");
        doReturn(1).when(userRepository).add(1, "guid", "name");

        commandService.processCommand(command);

        verify(userRepository).add(1, "guid", "name");
    }

    @Test
    void verifyCommandServiceThrowsExceptionWhenAddCommandUserIdIsNull() {
        Command command = new Command(ADD, null, null, null);
        doReturn(1).when(userRepository).add(null, null, null);

        Exception thrown = assertThrows(Exception.class, () -> commandService.processCommand(command));

        verify(userRepository, never()).add(null, null, null);
        assertEquals(IllegalArgumentException.class, thrown.getClass());
        assertEquals("USED_ID cannot be null", thrown.getMessage());
    }

    @Test
    void verifyCommandServiceInvokesGetAllWhenPrintAllCommand() {
        Command command = new Command(PRINT_ALL, null, null, null);
        doReturn(resultSet).when(userRepository).getAll();

        commandService.processCommand(command);

        verify(userRepository).getAll();
    }

    @Test
    void verifyCommandServiceInvokesRepoDeleteWhenDeleteCommand() {
        Command command = new Command(DELETE_ALL, null, null, null);
        doReturn(1).when(userRepository).deleteAll();

        commandService.processCommand(command);

        verify(userRepository).deleteAll();
    }
}