const express = require("express");
const bodyParser = require("body-parser");
const crypto = require("crypto");
const cors = require("cors");

class Block {
  constructor(index, timestamp, data, previousHash = "") {
    this.index = index;
    this.timestamp = timestamp;
    this.data = Array.isArray(data) ? data : [data]; // Ensure data is always an array
    this.previousHash = previousHash;
    this.nonce = 0;
    this.hash = this.calculateHash();
  }

  calculateHash() {
    return crypto
      .createHash("sha256")
      .update(
        this.index +
          this.timestamp +
          JSON.stringify(this.data) +
          this.previousHash +
          this.nonce
      )
      .digest("hex");
  }

  mineBlock(difficulty) {
    while (
      this.hash.substring(0, difficulty) !== Array(difficulty + 1).join("0")
    ) {
      this.nonce++;
      this.hash = this.calculateHash();
    }
  }

  updateData(newData) {
    this.data = Array.isArray(newData) ? newData : [newData]; // Ensure array format
    this.hash = this.calculateHash(); // 🔥 Hash changes due to new data
  }
}

class Blockchain {
  constructor() {
    this.chain = [this.createGenesisBlock()];
    this.difficulty = 3;
  }

  createGenesisBlock() {
    return new Block(0, Date.now(), "Genesis Block", "0");
  }

  getLatestBlock() {
    return this.chain[this.chain.length - 1];
  }

  addBlock(newBlock) {
    newBlock.previousHash = this.getLatestBlock().hash;
    newBlock.mineBlock(this.difficulty);
    this.chain.push(newBlock);
  }

  updateBlock(index, newData) {
    if (index < 1 || index >= this.chain.length) {
      return false; // Invalid block index
    }
  
    // Update the block data → this will change the current block's hash
    this.chain[index].updateData(newData);
  
    // Recalculate hashes for subsequent blocks
    for (let i = index + 1; i < this.chain.length; i++) {
      const currentBlock = this.chain[i];
      currentBlock.previousHash = this.chain[i - 1].hash; // Update previousHash
      currentBlock.hash = currentBlock.calculateHash(); // Recalculate the hash
    }
  
    return true; // Block updated successfully
  }
  
  

  isChainValid() {
    for (let i = 1; i < this.chain.length; i++) {
      const currentBlock = this.chain[i];
      const previousBlock = this.chain[i - 1];

      if (currentBlock.hash !== currentBlock.calculateHash()) {
        return false;
      }

      if (currentBlock.previousHash !== previousBlock.hash) {
        return false;
      }
    }
    return true;
  }
}

const blockchain = new Blockchain();
const app = express();
app.use(bodyParser.json());
app.use(cors());

// Get entire blockchain
app.get("/blocks", (req, res) => {
  res.json(blockchain.chain);
});

// Add new block
app.post("/addBlock", (req, res) => {
  const { transactions } = req.body;
  const newBlock = new Block(
    blockchain.chain.length,
    Date.now(),
    Array.isArray(transactions) ? transactions : [transactions]
  );
  blockchain.addBlock(newBlock);
  res.json(newBlock);
});

// Update block data (breaks the blockchain)
app.put("/updateBlock/:index", (req, res) => {
  const index = parseInt(req.params.index);
  const { data } = req.body;

  if (blockchain.updateBlock(index, data)) {
    res.json({
      message: "Block updated, but blockchain is now invalid!",
      block: blockchain.chain[index],
    });
  } else {
    res.status(400).json({ error: "Invalid block index" });
  }
});

// Validate blockchain
app.get("/validate", (req, res) => {
  const isValid = blockchain.isChainValid();
  res.json({ valid: isValid });
});

const PORT = 5000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
