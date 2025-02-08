import React, { useState, useEffect } from "react";
import axios from "axios";
import Block from "./Block";

const API_URL = "http://localhost:5000"; // Your backend URL

const MainPage = () => {
  const [blocks, setBlocks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [transactions, setTransactions] = useState("");
  const [originalHashes, setOriginalHashes] = useState({});

  const fetchBlocks = async () => {
    try {
      const response = await axios.get(`${API_URL}/blocks`);
      setBlocks(response.data);
    } catch (error) {
      console.error("Error fetching blocks:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchBlocks();
  }, []);

  const addBlock = async () => {
    if (!transactions.trim()) return;
    try {
      await axios.post(`${API_URL}/addBlock`, { transactions: [transactions] });
      setTransactions("");
      fetchBlocks();
    } catch (error) {
      console.error("Error adding block:", error);
    }
  };

  const onUpdate = async (index, newData) => {
    try {
      // Save original hash before updating
      setOriginalHashes((prev) => ({ ...prev, [index]: blocks[index].hash }));
  
      await axios.put(`${API_URL}/updateBlock/${index}`, { data: newData });
      fetchBlocks(); // Refresh UI
    } catch (error) {
      console.error("Error updating block:", error);
    }
  };

  // Marks a block and all subsequent blocks as invalid
  const checkInvalidBlocks = () => {
    let invalid = false;
    return blocks.map((block, index) => {
      if (index === 0) return false; // Genesis block is always valid
      
      // If the hash has been modified from its original, mark it and all after it invalid
      if (originalHashes[index] && originalHashes[index] !== block.hash) {
        invalid = true;
      }
  
      return invalid;
    });
  };
  
  const invalidBlocks = checkInvalidBlocks();

  return (
    <div className="bg-gray-900 min-h-screen p-6">
      <h1 className="text-3xl font-bold text-white mb-6">Blockchain Demo</h1>
      
      <div className="flex items-center">
        <input
          type="text"
          value={transactions}
          onChange={(e) => setTransactions(e.target.value)}
          placeholder="Enter transaction"
          className="border rounded p-2 w-2/3 mb-4 text-white bg-gray-700"
        />
        <button onClick={addBlock} className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-green-600">
          Add Block
        </button>
      </div>

      {isLoading ? (
        <p className="text-white">Loading blocks...</p>
      ) : (
        <div className="flex flex-wrap gap-4">
          {blocks.length > 0 ? (
            blocks.map((block, index) => (
              <Block
                key={block.index}
                block={block}
                onUpdate={onUpdate}
                isInvalid={invalidBlocks[index]} // All affected blocks turn red
              />
            ))
          ) : (
            <p className="text-gray-400">No blocks available.</p>
          )}
        </div>
      )}
    </div>
  );
};

export default MainPage;
