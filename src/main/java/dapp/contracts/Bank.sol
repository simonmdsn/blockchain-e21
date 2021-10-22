pragma solidity ^0.5.16;

contract Bank {

    // map of accounts to their balance
    mapping (address => uint) private balances;
    address public owner;

    constructor() public payable {
        owner = msg.sender;
    }

    function addAccount() public returns (uint) {
        return balances[msg.sender];
    }

    function deposit() public payable returns (uint) {
        balances[msg.sender] += msg.value;
        return balances[msg.sender];
    }

    function withdraw(uint amount) public returns (uint left) {
        if(amount <= balances[msg.sender]) {
            balances[msg.sender] -= amount;
            msg.sender.transfer(amount);
        }
        return balances[msg.sender];
    }

    function getBalance() public view returns (uint) {
        return balances[msg.sender];
    }
}
