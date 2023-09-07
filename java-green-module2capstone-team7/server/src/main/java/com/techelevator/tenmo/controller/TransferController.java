package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Transfer addTransfer(@RequestBody Transfer transfer) {
        return transferDao.addNewTransfer(transfer);
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/fromuser", method = RequestMethod.GET)
    public List<Transfer> showTransferFromUser(Principal principal) {
        return transferDao.getAllTransfersSentFromUser(principal.getName());
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/touser", method = RequestMethod.GET)
    public List<Transfer> showTransferToUser(Principal principal) {
        return transferDao.getAllTransfersReceivedByUser(principal.getName());
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfer get(@PathVariable int id) {
        return transferDao.getTransferById(id);
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public Transfer updateBalance(@RequestBody Transfer transfer){
        return transferDao.addNewTransfer(transfer);
    }
}
