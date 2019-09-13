using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using JWT.Algorithms;
using JWT.Builder;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using MoneyBankServer.Requests;
using MoneyBankServer.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace MoneyBankServer.Controllers
{
    [Route("api/[controller]")]
    public class LoginController : Controller
    {
        private JWTSettings jwtSettings;

        public LoginController(ILoginRepository loginRepository, IOptions<JWTSettings> options)
        {
            this.loginRepository = loginRepository;
            this.jwtSettings = options.Value;
        }

        private readonly ILoginRepository loginRepository;

        // GET: api/values
        [HttpPost]
        public IActionResult Login([FromBody]LoginRequest request)
        {
            if (!ModelState.IsValid) 
            {
                return BadRequest(ModelState);
            }
            Login login = loginRepository.GetLogin(request.Username, request.Password);
            if (login == null) 
            {
                return BadRequest();
            }
            var token = new JwtBuilder()
                .WithAlgorithm(new HMACSHA256Algorithm())
                .WithSecret(jwtSettings.SecretKey)
                .AddClaim("sub", login.Username)
                .AddClaim("username", login.Username)
                .AddClaim("customerName", login.CustomerName)
                .AddClaim("iss", jwtSettings.Issuer)
                .AddClaim("aud", jwtSettings.Audience)
                .AddClaim("nbf", ConvertToUnixTimestamp(DateTime.Now))
                .AddClaim("iat", ConvertToUnixTimestamp(DateTime.Now))
                .AddClaim("exp", ConvertToUnixTimestamp(DateTime.Now.AddDays(7)))
                .Build();

            return Ok(new LoginResponse { Token = token });
        }

        private static double ConvertToUnixTimestamp(DateTime date)
        {
            DateTime origin = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeKind.Utc);
            TimeSpan diff = date.ToUniversalTime() - origin;
            return Math.Floor(diff.TotalSeconds);
        }
    }
}
