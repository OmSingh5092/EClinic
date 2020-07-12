const express = require('express')
const router = express.Router();

const userVerification = require('../middlewares/userVerification')

const controller = require('../controllers/appointmentCtrl');
const validate = require('../middlewares/validate').appointmentValidation;

router.post('/add',userVerification ,controller.addAppointment);
router.get('/get',userVerification ,controller.getAppointment)
router.delete('/delete',userVerification,controller.deleteAppointment);

module.exports = router;