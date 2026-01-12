package com.florido.expense_tracker_api.controllers.DTOs;

public record ErroCampo(
        String campo,
        String erro
) {
}
