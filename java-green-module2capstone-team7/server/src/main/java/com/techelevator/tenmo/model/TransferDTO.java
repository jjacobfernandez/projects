package com.techelevator.tenmo.model;

public class TransferDTO
{
    private int transferId;
    private double transferAmount;
    private String senderName;
    private String receiverName;

    public int getTransferId() {
        return transferId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "transferId=" + transferId +
                ", transferAmount=" + transferAmount +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }
}
