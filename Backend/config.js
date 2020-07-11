const config = {
    google:{},
    database:{
        local:{
            DATABASE_HOST: process.env.DATABASE_HOST || 'localhost',
            DATABASE_NAME: process.env.DATABASE_NAME || 'eclinic-api',
            DATABASE_USERNAME: process.env.DATABASE_USERNAME || 'postgres',
            DATABASE_PASSWORD: process.env.DATABASE_PASSWORD || 'omsingh',
            DATABASE_PORT: process.env.DATABASE_PORT || 5433,
            DATABASE_DIALECT: process.env.DATABASE_DIALECT || 'postgres',
            NODE_ENV: process.env.NODE_ENV || 'development',
            SCHEMA: "public",
        },
        prod:{

        }
    },
}

module.exports = config;