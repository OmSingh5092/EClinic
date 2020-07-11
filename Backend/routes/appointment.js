const express = require('express')
const router = express.Router();

const userVerification = require('../middlewares/userVerification')

const controller = require('../controllers/appointmentCtrl');
const validate = require('../middlewares/validate').appointmentValidation;

router.post('/add',userVerification ,validate,controller.addAppointment);
router.get('/get',userVerification ,controller.getAppointment)

module.exports = router;