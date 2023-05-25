import React, { useState } from "react";
import NavBar from "./NavBar";
import Axios from "axios";
import { Button, TextField, Box, Typography, Snackbar } from "@mui/material";

export default function WalletRecharge() {
  const [amountError, setAmountError] = useState(true);
  const [amount, setAmount] = useState();
  const [open, setOpen] = React.useState(false);

  const changeAmountInputEventHandler = (event) => {
    const enteredAmount = event.target.value.trim();

    if (!enteredAmount || isNaN(enteredAmount) || enteredAmount <= 0) {
      setAmountError(true);
    } else {
      setAmountError(false);
      setAmount(enteredAmount);
    }
  };

  const rechargeEventHandler = (event) => {

    event.preventDefault();

    if (!amountError) {
      const apiUrl = "http://localhost:8099/api/wallet/recharge";

      const token = localStorage.getItem("Wallet__token");

      const bodyParameters = {
        amount: amount,
      };

      const config = {
        headers: { Authorization: token },
      };

      Axios.post(apiUrl, bodyParameters, config)
        .then((response) => {
          console.log(response);
          setAmount("");
          //   setBalance(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
      handleSnackBar();
    } else {
      alert("Enter Valid Amount");
    }
    
  };

  const handleSnackBar = () => {
    setOpen(true);
    setTimeout(() => {
      setOpen(false);
    }, 4000);
  };
  return (
    <div>
      <NavBar></NavBar>

      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          minHeight: "40vh",
          border: "1px solid #ccc",
          borderRadius: "5px",
        }}
      >
        <Box sx={{ padding: "1rem 5rem" }}>
          <Typography variant="h4" component="h4">
            Recharge Wallet
          </Typography>
          <Box
            sx={{
              marginTop: "1rem",
              display: "flex",
              alignItems: "center",
              justifyItems: "center",
            }}
            component="form"
            onSubmit={rechargeEventHandler}
          >
            <Box
              sx={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                width: "100%",
              }}
            >
              <TextField
                id="outlined-basic"
                label="Amount"
                variant="outlined"
                placeholder="Enter Amount"
                onChange={changeAmountInputEventHandler}
                value={amount}
              />
              <Button
                type="submit"
                variant="contained"
                sx={{ marginLeft: "0.5rem" }}
              >
                Recharge
              </Button>
            </Box>
          </Box>
        </Box>
      </Box>
      <Snackbar open={open} message={"Recharge Successfull"} />
    </div>
  );
}
