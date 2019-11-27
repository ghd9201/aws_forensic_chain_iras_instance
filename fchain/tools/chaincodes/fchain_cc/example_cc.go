/*
Copyright IBM Corp. 2016 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package main


import (
	"encoding/json"
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

var logger = shim.NewLogger("example_cc0")

type Evidence struct {
    ObjectType     string `json:"docType"`
	EvidenceId     string `json:"evidenceId"`               // key
	RegisterId     string `json:"registerId"`
	Description    string `json:"description"`
	CaseId         string `json:"caseId"`
	Hash           string `json:"hash"`
	RegisterTime   string `json:"registerTime"`
}

type Document struct {
    ObjectType     string `json:"docType"`
	DocumentId     string `json:"documentId"`               // key
	DocumentType   string `json:"documentType"`
	WriterId       string `json:"writerId"`
	WriteTime      string `json:"writeTime"`
	Description    string `json:"description"`
	CaseId         string `json:"caseId"`
	Hash           string `json:"hash"`
}

// I don't know it will
type TransferEvent struct {
    ObjectType     string `json:"docType"`
	TransferNo     string `json:"transferNo"`               // key
	EvidenceId     string `json:"evidenceId"`               // not key
	SenderId       string `json:"senderId"`
	SenderOrg      string `json:"senderOrg"`
	Description    string `json:"description"`
	ReceiverId     string `json:"receiverId"`
	ReceiverOrg    string `json:"receiverOrg"`
	CaseId         string `json:"caseId"`
	Hash           string `json:"hash"`
	TransferTime   string `json:"transferTime"`
}


// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

// Init - initialize the state
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response  {
	logger.Info("########### Upgrade fchain_cc0 Init ###########")

	_, args := stub.GetFunctionAndParameters()
	var A, B string    // Entities
	var Aval, Bval int // Asset holdings
	var err error

    if len(args) != 4 {
		return shim.Error("Incorrect number of arguments. Expecting 4")
	}

	// Initialize the chaincode
	A = args[0]
	Aval, err = strconv.Atoi(args[1])
	if err != nil {
		return shim.Error("Expecting integer value for asset holding")
	}
	B = args[2]
	Bval, err = strconv.Atoi(args[3])
	if err != nil {
		return shim.Error("Expecting integer value for asset holding")
	}
	logger.Infof("Aval = %d, Bval = %d\n", Aval, Bval)

	// Write the state to the ledger
	err = stub.PutState(A, []byte(strconv.Itoa(Aval)))
	if err != nil {
		return shim.Error(err.Error())
	}

	err = stub.PutState(B, []byte(strconv.Itoa(Bval)))
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success(nil)
}

// Invoke - Transaction makes payment of X units from A to B
func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	logger.Info("########### fchain_cc0 Invoke ###########")

	function, args := stub.GetFunctionAndParameters()

	if function == "delete" {
		// Deletes an entity from its state
		return t.delete(stub, args)
	}

	if function == "query" {
		// queries an entity state
		return t.query(stub, args)
	}

	if function == "addEvidence" {
    		// Add an entity to its state
    		return t.add(stub, args)
    }
    if function == "addDocument" {
    		// Add an entity to its state
    		return t.add(stub, args)
    }

	logger.Errorf("Unknown action, check the first argument, must be one of 'delete', 'query', or 'move'. But got: %v", args[0])
	return shim.Error(fmt.Sprintf("Unknown action, check the first argument, must be one of 'delete', 'query', or 'move'. But got: %v", args[0]))
}

func (t *SimpleChaincode) addEvidence(stub shim.ChaincodeStubInterface, args []string) pb.Response {

  logger.Info("function addEvidence called!!!");
	var ObjectType   string
	var EvidenceId   string
	var RegisterId   string
	var Description  string
    var CaseId       string
	var Hash         string
    var RegisterTime string

	if len(args) != 6 { // The number of input parameter should be 22
		return shim.Error("Incorrect number of arguments. Expecting 6, function followed by 6 names")
	}

	var err error

	EvidenceId = args[0]
	ReceiverId = args[1]
	Description = args[2]
	CaseId = args[3]
	Hash = args[4]
	RegisterTime = args[5]

	// ==== Create the object and marshal to JSON ====
	Evidence := &Evidence{"Evidence", EvidenceId, ReceiverId, Description, CaseId, Hash, RegisterTime}
	UserJSONasBytes, err := json.Marshal(Evidence)
	if err != nil {
		return shim.Error(err.Error())
	}

	// Write the state to the ledger
	err = stub.PutState(EvidenceId, UserJSONasBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success([]byte("Successfully save cp to the ledger"))
}

func (t *SimpleChaincode) addDocument(stub shim.ChaincodeStubInterface, args []string) pb.Response {

  logger.Info("function addDocument called!!!");
	var ObjectType   string
	var DocumentId   string
	var DocumentType string
	var WriterId     string
	var WriteTime    string
	var Description  string
    var CaseId       string
	var Hash         string

	if len(args) != 7 { // The number of input parameter should be 22
		return shim.Error("Incorrect number of arguments. Expecting 7, function followed by 7 names")
	}

	var err error

	DocumentId = args[0]
	DocumentType = args[1]
	WriterId = args[2]
	WriteTime = args[3]
	Description = args[4]
	CaseId = args[5]
	Hash = args[6]

	// ==== Create the object and marshal to JSON ====
	Document := &Document{"Document", DocumentId, DocumentType, WriterId, WriteTime, Description, CaseId, Hash}
	UserJSONasBytes, err := json.Marshal(Document)
	if err != nil {
		return shim.Error(err.Error())
	}

	// Write the state to the ledger
	err = stub.PutState(DocumentId, UserJSONasBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success([]byte("Successfully save cp to the ledger"))
}

// Deletes an entity from state
func (t *SimpleChaincode) delete(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	A := args[0]

	// Delete the key from the state in ledger
	err := stub.DelState(A)
	if err != nil {
		return shim.Error("Failed to delete state")
	}

	return shim.Success(nil)
}

// Query callback representing the query of a chaincode
func (t *SimpleChaincode) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	var A string // Entities
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
	}

	A = args[0]

	// Get the state from the ledger
	Avalbytes, err := stub.GetState(A)
	if err != nil {
		jsonResp := "{\"Error\":\"Failed to get state for " + A + "\"}"
		return shim.Error(jsonResp)
	}

	if Avalbytes == nil {
		jsonResp := "{\"Error\":\"Nil amount for " + A + "\"}"
		return shim.Error(jsonResp)
	}

	jsonResp := "{\"Name\":\"" + A + "\",\"Value\":\"" + string(Avalbytes) + "\"}"
	logger.Infof("Query Response:%s\n", jsonResp)
	return shim.Success(Avalbytes)
}

func main() {
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		logger.Errorf("Error starting Fchain chaincode: %s", err)
	}
}
