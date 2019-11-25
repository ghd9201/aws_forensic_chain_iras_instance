#!/bin/bash

echo 'Create Channel..'

peer channel create -o orderer.fchain.com:7050 -c fchannel -f ./channel-artifacts/fchannel.tx \
--cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/fchain.com/orderers/orderer.fchain.com/msp/cacerts/ca.fchain.com-cert.pem


echo 'Done'

echo 'Join Channel..'

CORE_PEER_ADDRESS=p0.org1.fchain.com:7051 peer channel join -b fchannel.block
CORE_PEER_ADDRESS=p1.org1.fchain.com:7051 peer channel join -b fchannel.block

CORE_PEER_ADDREE=p0.org1.fchain.com:7051 peer channel update --channelID fchannel --file ./channel-artifacts/fchannel.anchor.org1 --orderer orderer.fchain.com:7050

#CORE_PEER_ADDRESS=peer0.org1.example.com:7051 peer channel join -b mychannel2.block
#CORE_PEER_ADDRESS=peer0.org1.example.com:7051 peer channel join -b mychannel3.block
#CORE_PEER_ADDRESS=peer0.org1.example.com:7051 peer channel join -b mychannel4.block
#CORE_PEER_ADDRESS=peer0.org1.example.com:7051 peer channel join -b mychannel5.block

echo 'Done'
