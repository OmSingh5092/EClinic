const Sequelize = require('sequelize');
const sequelize = require('../database');

const appointment = sequelize.define('appointment',{
    appointment_id:{
        type:Sequelize.BIGINT,
        primaryKey:true,
        allowNull:false,
        autoIncrement:true,
    },

    prescription_path:{
        type:Sequelize.TEXT,
    },

    text:{
        type:Sequelize.TEXT,
    },

    //Status
    status:{
        type:Sequelize.BOOLEAN,
    }

    //Payment Status

})

module.exports = appointment;