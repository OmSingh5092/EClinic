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
    },

    //Payment Status

    payment_status:{
        type:Sequelize.BOOLEAN,
    },

    //New Prescription
    new_prescription_path:{
        type:Sequelize.TEXT,
    },

    //Interaction method

    interaction_method:{
        type:Sequelize.TEXT,
    },

    //Appointment Schedule date
    date:{
        type:Sequelize.DATE
    },

    //Priority

    priority:{
        type:Sequelize.INTEGER
    }

})

module.exports = appointment;