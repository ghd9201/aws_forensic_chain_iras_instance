#!/bin/bash

#echo 'Install ChainCode'
#CHAINCODE_DIR=github.com/hyperledger/fabric/examples/chaincode/go/
#CORE_PEER_ADDRESS=peer0.org1.iras.com:7051 peer chaincode install -n $1 -v $2 -p $CHAINCODE_DIR/$1_cc

#echo 'Done'

echo 'Instantiate ChainCode'

CORE_PEER_ADDRESS=p0.org1.fchain.com:7051 peer chaincode instantiate -o orderer.fchain.com:7050 \
--cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/fchain.com/orderers/orderer.fchain.com/msp/cacerts/ca.fchain.com-cert.pem \
-C fchannel -n $1 -v $2 -c '{"Args":["init","a","200","b","200"]}' -P "OR   ('Org1MSP.member')"





#CORE_PEER_ADDRESS=peer0.org1.example.com:7051 peer chaincode instantiate -o orderer.example.com:7050 \
#--cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/cacerts/ca.example.com-cert.pem \
#-C mychannel2 -n $1 -v $2 -c '{"Args":["init","a","200","b","200"]}' -P "OR   ('Org1MSP.member')"

#CORE_PEER_ADDRESS=peer0.org1.example.com:7051 peer chaincode instantiate -o orderer.example.com:7050 \
#--cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/cacerts/ca.example.com-cert.pem \
#-C mychannel3 -n $1 -v $2 -c '{"Args":["init","a","200","b","200"]}' -P "OR   ('Org1MSP.member')"

echo 'Done'
