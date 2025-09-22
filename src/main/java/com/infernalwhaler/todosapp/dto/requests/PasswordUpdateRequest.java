package com.infernalwhaler.todosapp.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public class PasswordUpdateRequest {

    @NotEmpty(message = "Old Password is mandatory")
    @Size(min = 4, max = 30, message = "Old Password must be at least 4 characters long")
    private String oldPassword;

    @NotEmpty(message = "New Password is mandatory")
    @Size(min = 4, max = 30, message = "New Password must be at least 4 characters long")
    private String newPassword;

    @NotEmpty(message = "New Password is mandatory")
    @Size(min = 4, max = 30, message = "New Password must be at least 4 characters long")
    private String confirmNewPassword;

    public PasswordUpdateRequest(String oldPassword, String newPassword, String confirmNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
