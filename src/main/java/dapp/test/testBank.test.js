const Bank = artifacts.require("Bank");

contract("Bank", (accounts) => {
    const bob = accounts[1];
    const alice = accounts[2];
    const simon = accounts[3];
    const torben = accounts[4];

    it("should add all accounts to the bank and check that balance is 0", async () => {
        const bank = await Bank.deployed();
        
        await bank.addAccount({from: bob});
        const bobBalance = await bank.getBalance({from: bob});
        assert.equal(bobBalance, 0, "initial balance is zero");

        await bank.addAccount({from: alice});
        const aliceBalance = await bank.getBalance({from: alice});
        assert.equal(aliceBalance, 0, "initial balance is zero");

        await bank.addAccount({from: simon});
        const simonBalance = await bank.getBalance({from: simon});
        assert.equal(simonBalance, 0, "initial balance is zero");

        await bank.addAccount({from: torben});
        const torbenBalance = await bank.getBalance({from: torben});
        assert.equal(torbenBalance, 0, "initial balance is zero");
    });

    it("should deposit 1 into the account balance", async () => {
        const bank = await Bank.deployed();

        const deposit = 1;
        
        await bank.deposit({from: bob, value: deposit})
        const bobBalance = await bank.getBalance({from: bob});
        assert.equal(bobBalance, 1, "balance after deposit is 1");

        await bank.deposit({from: alice, value: deposit})
        const aliceBalance = await bank.getBalance({from: alice});
        assert.equal(aliceBalance, 1, "balance after deposit is 1");
        
        await bank.deposit({from: simon, value: deposit})
        const simonBalance = await bank.getBalance({from: simon});
        assert.equal(simonBalance, 1, "balance after deposit is 1");

        await bank.deposit({from: torben, value: deposit})
        const torbenBalance = await bank.getBalance({from: torben});
        assert.equal(torbenBalance, 1, "balance after deposit is 1");
    });

    it("should deposit and withdraw 1 from the account balance, remaining balance is therefore 0", async () => {
        const bank = await Bank.deployed();

        const depositWithdraw = 1;

        await bank.deposit({from: bob, value: depositWithdraw});
        const bobDepositBalance = await bank.getBalance({from: bob});
        await bank.withdraw(depositWithdraw, {from: bob});
        const bobWithdrawBalance = await bank.getBalance({from: bob});
        assert.equal(bobDepositBalance - depositWithdraw, bobWithdrawBalance, "balance after deposit and withdrawing is 0");

        await bank.deposit({from: alice, value: depositWithdraw});
        const aliceDepositBalance = await bank.getBalance({from: alice});
        await bank.withdraw(depositWithdraw, {from: alice});
        const aliceWithdrawBalance = await bank.getBalance({from: alice});
        assert.equal(aliceDepositBalance - depositWithdraw, aliceWithdrawBalance, "balance after deposit and withdrawing is 0");

        await bank.deposit({from: simon, value: depositWithdraw});
        const simonDepositBalance = await bank.getBalance({from: simon});
        await bank.withdraw(depositWithdraw, {from: simon});
        const simonWithdrawBalance = await bank.getBalance({from: simon});
        assert.equal(simonDepositBalance - depositWithdraw, simonWithdrawBalance, "balance after deposit and withdrawing is 0");

        await bank.deposit({from: torben, value: depositWithdraw});
        const torbenDepositBalance = await bank.getBalance({from: torben});
        await bank.withdraw(depositWithdraw, {from: torben});
        const torbenWithdrawBalance = await bank.getBalance({from: torben});
        assert.equal(torbenDepositBalance - depositWithdraw, torbenWithdrawBalance, "balance after deposit and withdrawing is 0");

    });
});