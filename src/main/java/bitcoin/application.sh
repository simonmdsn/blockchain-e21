#!/bin/sh

cd /opt/bitcoin/bin

./bitcoind -regtest -daemon -fallbackfee=0.1
sleep 2

echo "Creating wallet..."
echo "Name of your wallet?"

read wallet

echo "Generating your wallet $wallet"

sleep 2
./bitcoin-cli -regtest createwallet "$wallet"


echo "Generating blocks..."

sleep 2
./bitcoin-cli -regtest -generate 101


echo "Balance in wallet $wallet"

sleep 2
./bitcoin-cli -regtest getbalance


echo "Generating a new address..."

new_address=$(./bitcoin-cli -regtest getnewaddress)

sleep 2
echo "Sending some bitcoin to the new address $new_address"

./bitcoin-cli -regtest sendtoaddress $new_address 10.00

echo "Following are the unspend transcations:"

sleep 2
./bitcoin-cli -regtest listunspent 0