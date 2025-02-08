import React, { useState } from "react";

const Block = ({ block, onUpdate, isInvalid }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [newData, setNewData] = useState(block.data);

  const handleUpdate = () => {
    onUpdate(block.index, newData);
    setIsEditing(false);
  };

  return (
    <div
      className={`border p-4 rounded-lg shadow-md w-64 transition-all duration-300
        ${isInvalid ? "bg-red-500 border-red-700 text-white" : "bg-gray-800 border-gray-600 text-gray-300"}`}
    >
      <h3 className="text-lg font-bold text-blue-400 mb-2">Block #{block.index}</h3>
      <div className="space-y-2">
        <p className="text-sm"><b>Nonce:</b> {block.nonce}</p>
        {isEditing ? (
          <textarea
            value={newData}
            onChange={(e) => setNewData(e.target.value)}
            className="w-full bg-gray-700 text-white p-1 rounded"
          />
        ) : (
          <p className="text-sm"><b>Data:</b> {block.data || "None"}</p>
        )}
        <p className="text-sm break-words"><b>Prev Hash:</b> {block.previousHash.substring(0, 20)}...</p>
        <p className="text-sm break-words"><b>Hash:</b> {block.hash.substring(0, 20)}...</p>
      </div>
      {isEditing ? (
        <button
          onClick={handleUpdate}
          className="mt-2 bg-green-500 text-white px-2 py-1 rounded hover:bg-green-600"
        >
          Save
        </button>
      ) : (
        <button
          onClick={() => setIsEditing(true)}
          className="mt-2 bg-blue-500 text-white px-2 py-1 rounded hover:bg-blue-600"
        >
          Edit
        </button>
      )}
    </div>
  );
};

export default Block;
