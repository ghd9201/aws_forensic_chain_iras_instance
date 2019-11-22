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

type File struct {
	ObjectType     string `json:"docType"`
	userName       string `json:"useName"`
	fileName       string `json:"fileName"`
	fileHash       string `json:"fileHash"`
	fileSize       string `json:"fileSize"`
	security       string `json:"security"`
	accessType     string `json:"accessType"`
	date           string `json:"date"`
	returnType     string `json:"returnType"`
	description    string `json:"description"`
}

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

// Init - initialize the state
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response  {
	logger.Info("########### Upgrade iras_cc0 Init ###########")

	_, args := stub.GetFunctionAndParameters()
	var err error

        var ObjectType     string
        var userName       string
        var fileName       string
        var fileHash       string
        var fileSize       string
        var security       string
        var accessType     string
        var date           string
        var returnType     string
        var description    string


        if len(args) != 10 {
		return shim.Error("Incorrect number of arguments. Expecting 10")
	}

	ObjectType = args[0]
        userName = args[1]
        fileName = args[2]
        fileHash = args[3]
        fileSize, err = strconv.Atoi(args[4])
        security = args[5]
        accessType = args[6]
        date = args[7]
        returnType = args[8]
        description = args[9]

	// Initialize the chaincode
	if err != nil {
		return shim.Error("Expecting integer value for asset holding")
	}

	// Write the state to the ledger
	err = stub.PutState(ObjectType, userName, fileName, fileHash, fileSize, security, accessType, date, returnType, description)
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success(nil)
}

// Invoke - Transaction makes payment of X units from A to B
func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	logger.Info("########### iras_cc0 Invoke ###########")

	function, args := stub.GetFunctionAndParameters()

	if function == "delete" {
		// Deletes an entity from its state
		return t.delete(stub, args)
	}

	if function == "query" {
		// queries an entity state
		return t.query(stub, args)
	}

	if function == "add" {
		// Add an entity to its state
		return t.add(stub, args)
	}

	logger.Errorf("Unknown action, check the first argument, must be one of 'delete', 'query', or 'add'. But got: %v", args[0])
	return shim.Error(fmt.Sprintf("Unknown action, check the first argument, must be one of 'delete', 'query', or 'add'. But got: %v", args[0]))
}


func (t *SimpleChaincode) add(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	// must be an invoke
	var err error

	var ObjectType     string
	var userName       string
	var fileName       string
	var fileHash       string
	var fileSize       string
	var security       string
	var accessType     string
	var date           string
	var returnType     string
	var description    string

	if len(args) != 10 {
		return shim.Error("Incorrect number of arguments. Expecting 10, function followed by 1 ObjectType,
		1 userName, 1 fileName, 1 fileHash, 1 fileSize, 1 security, 1 accessType, 1 date, 1 returnType, 1 description")
	}

	ObjectType = args[0]
	userName = args[1]
	fileName = args[2]
	fileHash = args[3]
	fileSize, err = strconv.Atoi(args[4])
	security = args[5]
	accessType = args[6]
	date = args[7]
	returnType = args[8]
	description = args[9]

	// Write the state to the ledger
	err = stub.PutState(ObjectType, userName, fileName, fileHash, fileSize, security, accessType, date, returnType, description)
	if err != nil {
		return shim.Error(err.Error())
	}

    return shim.Success([]byte("file add succeed!!!"))
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

	jsonResp := "{\"Name\":\"" + A + "\",\"Amount\":\"" + string(Avalbytes) + "\"}"
	logger.Infof("Query Response:%s\n", jsonResp)
	return shim.Success(Avalbytes)
}

func main() {
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		logger.Errorf("Error starting Simple chaincode: %s", err)
	}
}
