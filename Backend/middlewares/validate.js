const Joi = require('@hapi/joi')

module.exports.appointmentValidation = (req,res,next)=>{
    const appointment = req.body;
    const schema = Joi.object({
        doctor_id:Joi.required(),
    })

    const {data,error} = schema.validate(appointment)

    if (error) {
        console.log(error)
        return res.status(400).json({
            success: false,
            error: 'Invalid fields constraints. Bad request',
            details: error.details[0]
        });
    } else {
        return next();
    }
}