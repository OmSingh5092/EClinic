//Models

const patient = require('./patient');
const appointment = require('./appointment')
const doctor = require('./doctor')

//Doing table associations

patient.hasMany(appointment,{ foreignKey: 'patient_id', onDelete: 'CASCADE', constraints: false, allowNull: false })
appointment.belongsTo(patient,{ foreignKey: 'patient_id', onDelete: 'CASCADE', constraints: false, allowNull: false })

doctor.hasMany(appointment,{ foreignKey: 'doctor_id', onDelete: 'CASCADE', constraints: false, allowNull: false })
appointment.belongsTo(doctor,{ foreignKey: 'doctor_id', onDelete: 'CASCADE', constraints: false, allowNull: false })