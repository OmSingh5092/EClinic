const Sequelize = require('sequelize');
const sequelize = require('../database');

const doctor = sequelize.define('doctor',{
    doctor_id:{
        type:Sequelize.BIGINT,
        primaryKey:true,
        allowNull:false,
        autoIncrement:true,
    },

    //Contact Details
    phone_number:{
        type:Sequelize.TEXT,
    },
    email:{
        type:Sequelize.TEXT,
        allowNull:false
    },

    //Verification
    doctor_name:{
        type:Sequelize.TEXT,
        allowNull:false,
    },
    registration_number:{
        type:Sequelize.TEXT,
    },
    year_of_registration:{
        type:Sequelize.TEXT,
    },
    state_medical_council:{
        type:Sequelize.TEXT,
    },

    //Profile Photo
    photo_path:{
        type:Sequelize.TEXT,
    },

    //Category
    category:{
        type:Sequelize.TEXT
    },

    //Fees
    fees_priority:{
        type:Sequelize.INTEGER,
    },

    fees_general:{
        type:Sequelize.INTEGER,
    },

    //About
    about:{
        type:Sequelize.TEXT
    },

    created_at: {
        type: Sequelize.DATE,
        allowNull: false,
        defaultValue: sequelize.literal('CURRENT_TIMESTAMP')
    },
    updated_at: Sequelize.DATE,
    deleted_at: Sequelize.DATE

})

module.exports = doctor;