const Sequelize = require('sequelize');
const config = require('../config').database.local;


const sequelize = new Sequelize(
    config.DATABASE_NAME,
    config.DATABASE_USERNAME,
    config.DATABASE_PASSWORD,
    {
        host:config.DATABASE_HOST,
        port:config.DATABASE_PORT,
        dialect: config.DATABASE_DIALECT,
        //Removed to test local database without ssl
        /*dialectOptions: {
            ssl: {
            require: true,
            rejectUnauthorized: false
            } 
        },  */
        operatorAliases: false,
        // socketPath : env.SOCKET_PATH,
        dialectOptions: env.DIALECT_OPTIONS
        //  logging: false
    }
)

sequelize.sync({ force: true })
  .then(() => {
    console.log(`Database & tables created!`)
})

module.exports=sequelize;