import React, { useState } from "react";
import NavBar from "./NavBar";
import Axios from "axios";
import { Box, TextField, Button, Snackbar } from "@mui/material";

export default function WalletAmountTransfer() {
  const [username, setUsername] = useState("");
  const [amount, setAmount] = useState();

  const [amountError, setAmountError] = useState(true);
  const [usernameError, setUsernameError] = useState(true);

  const [displayResult, setDisplayResult] = useState("");
  const [isProcessed, setIsProcessed] = useState(false);

  const changeUsernameInputEventHandler = (event) => {
    setUsername(event.target.value);
    if (event.target.value.trim().length > 0) setUsernameError(false);
  };

  const changeAmountInputEventHandler = (event) => {
    setAmount(event.target.value);
    if (event.target.value.trim().length > 0) setAmountError(false);
  };

  const handleSnackBar = () => {
    setIsProcessed(true);
    setTimeout(() => {
      setIsProcessed(false);
    }, 4000);
  };
  const transferAmountHandler = (event) => {
    event.preventDefault();

    if (!amountError && !usernameError) {
      const apiUrl = "http://localhost:8099/api/wallet/pay";

      const token = localStorage.getItem("Wallet__token");

      const bodyParameters = {
        username: username,
        amount: amount,
      };

      const config = {
        headers: { Authorization: token },
      };

      Axios.post(apiUrl, bodyParameters, config)
        .then((response) => {
          setDisplayResult(response.data);
          setUsername("");
          setAmount("");
        })
        .catch((error) => {
          alert(error.response.data);
        });
      handleSnackBar();
    } else if (usernameError && amountError) {
      alert("Enter valid username and amount");
    } else if (usernameError) {
      alert("Enter valid username");
    } else {
      alert("Enter valid amount");
    }
  };
  return (
    <div>
      <NavBar />

      <Box
        sx={{
          display: "flex",
          minHeight: "40vh",
          alignItems: "center",
          justifyContent: "center",
          width: "100%",
          border: "1px solid #ccc",
        }}
      >
        <Box
          component="form"
          onSubmit={transferAmountHandler}
          noValidate
          sx={{ mt: 1, maxWidth: "400px" }}
        >
          <TextField
            margin="normal"
            required
            fullWidth
            label="Username"
            name="username"
            id="transaction_username"
            type="text"
            placeholder="Enter Username"
            onChange={changeUsernameInputEventHandler}
            value={username}
            autoComplete="username"
            autoFocus
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="amount"
            id="transaction_amount"
            type="number"
            placeholder="Enter amount"
            onChange={changeAmountInputEventHandler}
            value={amount}
            label="Amount"
          />

          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Transfer
          </Button>
        </Box>
        <Snackbar open={isProcessed} message={displayResult} />
      </Box>
    </div>
  );
}
