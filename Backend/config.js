require("dotenv").config();

const config = {
  gcp: {
    clientId:
      "541374752269-86ein6vehn2elteuea39arj5nnaok92o.apps.googleusercontent.com",
    clientSecret: "iKd6K67CdlmgVEvKHSclJ9Mf",
  },
  database: {
    local: {
      DATABASE_HOST: process.env.DATABASE_HOST || "localhost",
      DATABASE_NAME: process.env.DATABASE_NAME || "eclinic",
      DATABASE_USERNAME: process.env.DATABASE_USERNAME || "postgres",
      DATABASE_PASSWORD: process.env.DATABASE_PASSWORD || "omsingh",
      DATABASE_PORT: process.env.DATABASE_PORT || 5433,
      DATABASE_DIALECT: process.env.DATABASE_DIALECT || "postgres",
      NODE_ENV: process.env.NODE_ENV || "development",
      SCHEMA: "public",
    },
    prod: {},
  },

  app: {
    local: {
      port: process.env.PORT || 4000,
    },
  },
  jwt: {
    jwtKey: process.env.JWT_KEY || "ECLINIC",
  },
};

module.exports = config;
