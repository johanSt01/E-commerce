package com.compraClick.DTO.User;

public record ResetPasswordDTO(
        String email,
        String resetCode,
        String newPassword) {
}
