package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.util.List;

public interface TransferDao {
    Transfer addNewTransfer(Transfer transfer);
    List<Transfer> getAllTransfersSentFromUser(String username);

    List<Transfer> getAllTransfersReceivedByUser(String username);

    Transfer getTransferById(int id);
}
