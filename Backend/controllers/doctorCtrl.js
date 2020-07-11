const table = require('../database/models/doctor');

module.exports.getDoctorProfile = (req,res)=>{
    const id = req.user.id;

    return table.findAll({
        where:{
            doctor_id:id,
        }
    }).then((userData)=>{
        return res.json({
            success:true,
            profile:userData,
        })
    }).catch((err)=>{
        console.log(err);
        return res.json({
            success:false,
            msg:"Internal Server Error"
        })
    })
}