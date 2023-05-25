import React, { useState, useEffect } from "react";
import NavBar from "./NavBar";
import Axios from "axios";
import { Box, Typography } from "@mui/material";
export default function WalletBalance() {
  const [balance, setBalance] = useState(0);
  const [isFetched, setIsFetched] = useState(false);
  useEffect(() => {
    const checkBalanceHandler = () => {
      const apiUrl = "http://localhost:8099/api/wallet/show-balance";
      const token = localStorage.getItem("Wallet__token");
      const config = {
        headers: { Authorization: token },
      };
      Axios.get(apiUrl, config)
        .then((response) => {
          console.log(response);
          setBalance(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
      setIsFetched(true);
    };
    checkBalanceHandler();
  }, []);

  return (
    <div>
      <NavBar />
      {!isFetched ? (
        <>Please wait...</>
      ) : (
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            flexDirection: "column",
            minHeight: "40vh",
            width: "100%",
            border: "1px solid #ccc",
          }}
        >
          <Typography variant="h6" component="h6">
            Wallet Balance : {balance}
          </Typography>
        </Box>
      )}
    </div>
  );
}
