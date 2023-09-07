package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao jdbcAccountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Account account;

    @Override
    public Transfer addNewTransfer(Transfer transfer) {
        String sql1 = "INSERT INTO transfer (transfer_amount, sender_name, receiver_name) " +
                "VALUES (?, ?, ?) RETURNING transfer_id;";
        Integer newTransferId = jdbcTemplate.queryForObject(sql1, Integer.class, transfer.getTransferAmount(),
                transfer.getSenderName(), transfer.getReceiverName());
        transfer.setTransferId(newTransferId);

        String sql = "SELECT balance FROM account \n" +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id\n" +
                "WHERE username = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transfer.getSenderName());
        double trialBalance = 0;
        if (result.next()){
            trialBalance = result.getDouble("balance");
        }
        trialBalance -= transfer.getTransferAmount();

        if (trialBalance < 0 || transfer.getTransferAmount() <= 0 ) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Insufficient funds/Transfer amount cannot be below zero");
        }else if(transfer.getSenderName().equals(transfer.getReceiverName())){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot send money to yourself");
        }else {
            // run an SQL that increases the balance of the recipient
        /*
        UPDATE account SET balance = balance + ? WHERE ....
         */
            String sql2 = "UPDATE account \n" +
                    "SET balance = balance + ?  \n" +
                    "WHERE user_id IN (SELECT account.user_id FROM account \n" +
                    "\t\t\t\t  JOIN tenmo_user ON tenmo_user.user_id = account.user_id \n" +
                    "\t\t\t\t  JOIN transfer ON transfer.receiver_name = tenmo_user.username \n" +
                    "\t\t\t\t  WHERE receiver_name = ? AND transfer_amount = ?)";
            int rowsAffected = jdbcTemplate.update(sql2, transfer.getTransferAmount(), transfer.getReceiverName(), transfer.getTransferAmount());

            // run an SQL that decreases the balance of the sender
        /*
        UPDATE account SET balance = balance - ? WHERE ....
         */
            String sql3 = "UPDATE account \n" +
                    "SET balance = balance - ?  \n" +
                    "WHERE user_id IN (SELECT account.user_id FROM account \n" +
                    "\t\t\t\t  JOIN tenmo_user ON tenmo_user.user_id = account.user_id \n" +
                    "\t\t\t\t  JOIN transfer ON transfer.sender_name = tenmo_user.username \n" +
                   "\t\t\t\t  WHERE sender_name = ? AND transfer_amount = ?)";
            int rowsAffected1 = jdbcTemplate.update(sql3, transfer.getTransferAmount(), transfer.getSenderName(), transfer.getTransferAmount());

        // to run the updates use the jdbcTemplate.update(...)

           return transfer;
        }
    }

    @Override
    public List<Transfer> getAllTransfersSentFromUser(String username) {
        String sql = "SELECT transfer_id, transfer_amount, sender_name, receiver_name " +
                "FROM transfer WHERE sender_name = ?;";
        List<Transfer> transfers = new ArrayList<>();
        SqlRowSet resultList = jdbcTemplate.queryForRowSet(sql, username);
        while (resultList.next()){
            transfers.add(mapRowToTransfer(resultList));
        }
        return transfers;
    }

    @Override
    public List<Transfer> getAllTransfersReceivedByUser(String username) {
        String sql = "SELECT transfer_id, transfer_amount, sender_name, receiver_name " +
                "FROM transfer WHERE receiver_name = ?;";
        List<Transfer> transfers = new ArrayList<>();
        SqlRowSet resultList = jdbcTemplate.queryForRowSet(sql, username);
        while (resultList.next()){
            transfers.add(mapRowToTransfer(resultList));
        }
        return transfers;
    }

    @Override
    public Transfer getTransferById(int id) {
        Transfer transfer = null;
       String sql ="SELECT transfer_id, transfer_amount, sender_name, receiver_name " +
                    "FROM transfer " +
                    "WHERE transfer_id = ?;";

       SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

       if (result.next())
       {
           transfer = mapRowToTransfer(result);
       }
       return transfer;
    }



    private Transfer mapRowToTransfer(SqlRowSet rowSet)
    {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferAmount(rowSet.getDouble("transfer_amount"));
        transfer.setSenderName(rowSet.getString("sender_name"));
        transfer.setReceiverName(rowSet.getString("receiver_name"));
        return transfer;
    }

}
