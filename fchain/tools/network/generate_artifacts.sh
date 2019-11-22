#!/bin/bash

export FABRIC_CFG_PATH=$PWD

#genesis.block 파일이 생성 될 channel-artifacts 디렉토리를 생성한다.
mkdir ./channel-artifacts

echo "--- Fchain genesis.block  ---"
#orderer genesis block: 프로필에 지정된 이름은 configtx.yaml 의 "OrdererGenesisProfile" 이다.
/home/ubuntu/go/src/github.com/hyperledger/fabric/scripts/bin/configtxgen -profile FchainGenesis -outputBlock ./channel-artifacts/genesis.block -channelID systemchannel

echo "--- organization fchannel  ---"
#channel configuration transaction: 프로필에 지정된 이름은 configtx.yaml의 "ChannelProfile" 이다.
/home/ubuntu/go/src/github.com/hyperledger/fabric/scripts/bin/configtxgen -profile FchainChannel -outputCreateChannelTx ./channel-artifacts/fchannel.tx -channelID fchannel


echo "--- organization Org1 anchor ---"
~/go/src/github.com/hyperledger/fabric-samples/bin/configtxgen -asOrg Org1MSP -channelID fchannel -configPath . -outputAnchorPeersUpdate ./channel-artifacts/fchannel.anchor.org1 -profile FchainChannel

echo "--- organization Org2 anchor ---"
~/go/src/github.com/hyperledger/fabric-samples/bin/configtxgen -asOrg Org2MSP -channelID fchannel -configPath . -outputAnchorPeersUpdate ./channel-artifacts/fchannel.anchor.org2 -profile FchainChannel

echo "--- organization Org3 anchor ---"
~/go/src/github.com/hyperledger/fabric-samples/bin/configtxgen -asOrg Org3MSP -channelID fchannel -configPath . -outputAnchorPeersUpdate ./channel-artifacts/fchannel.anchor.org3 -profile FchainChannel

