package ca.uqam.mgl7361.lel.gp1.common.dtos.order;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;

public record OrderRequest(AccountDTO account, CartDTO cart) {
}