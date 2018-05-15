const AUTH_URL = `https://platform.backend.povertystoplight.org/oauth/token`;
const BASE_URL = `https://platform.backend.povertystoplight.org`;
const creds = require("./creds.json");
const {
  username,
  password,
  grant_type,
  accessToken,
  clientId,
  clientSecret
} = creds;

module.exports = {
  AUTH_URL: process.env.AUTH_URL || AUTH_URL,
  BASE_URL: process.env.BASE_URL || BASE_URL,
  username: process.env.USERNAME || username,
  password: process.env.PASSWORD || password,
  grant_type: process.env.GRANT_TYPE || grant_type,
  accessToken: process.env.ACCESS_TOKEN || accessToken,
  clientId: process.env.CLIENT_ID || clientId,
  clientSecret: process.env.CLIENT_SECRET || clientSecret
};
