import React, { useState, useEffect } from "react";
import Axios from "axios";
import NavBar from "./NavBar";
import {
  TableContainer,
  Table,
  Paper,
  TableCell,
  TableHead,
  TableBody,
  TableRow,
} from "@mui/material";
export default function ShowTransactions() {
  const [transactionsList, setTransactionsList] = useState([]);
  useEffect(() => {
    async function fetchTransactions() {
      const apiUrl = "http://localhost:8099/api/wallet/show-all-transactions";

      const token = localStorage.getItem("Wallet__token");

      const config = {
        headers: { Authorization: token },
      };

      await Axios.get(apiUrl, config)
        .then((response) => {
          console.log(response);
          setTransactionsList(response.data);
          //   setBalance(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
    fetchTransactions();
  }, []);

  return (
    <div>
      <NavBar />
      {transactionsList.length === 0 && (
        <div
          className="ListContainer"
          style={{
            border: "4px solid black",
            height: "200px",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <p>No recent Transactions</p>
        </div>
      )}
      {transactionsList.length !== 0 && (
        <div className="ListContainer">
          <TableContainer component={Paper} stickyHeader>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell align="center">Transaction Type</TableCell>
                  <TableCell align="center">Username</TableCell>
                  <TableCell align="center">Debited From</TableCell>
                  <TableCell align="center">Credited To</TableCell>
                  <TableCell align="center">Amount</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {transactionsList.map((value, index) => (
                  <TableRow
                    key={value["id"]}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell align="center">
                      {value.transactionType}
                    </TableCell>
                    <TableCell align="center">{value.username}</TableCell>
                    <TableCell align="center">{value.debitedFrom}</TableCell>
                    <TableCell align="center">{value.creditedTo}</TableCell>
                    <TableCell align="center">{value.amount}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
      )}
    </div>
  );
}
