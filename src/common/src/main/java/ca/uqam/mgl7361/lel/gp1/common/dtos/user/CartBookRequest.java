package ca.uqam.mgl7361.lel.gp1.common.dtos.user;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;

public record CartBookRequest(AccountDTO account, BookDTO book) {
    public String toString() {
        return "CartBookRequest(AccountDTO=" + account + ", BookDTO=" + book + ")";
    }
}