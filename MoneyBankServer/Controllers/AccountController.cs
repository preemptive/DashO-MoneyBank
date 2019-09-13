using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MoneyBankServer.Services;
using MoneyBankServer.Models;
using MoneyBankServer.Responses;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace MoneyBankServer.Controllers
{
    [Authorize(AuthenticationSchemes = Microsoft.AspNetCore.Authentication.JwtBearer.JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/[controller]")]
    public class AccountController : Controller
    {
        private IBankingDataRepository bankingDataRepository;

        public AccountController(IBankingDataRepository bankingDataRepository) 
        {
            this.bankingDataRepository = bankingDataRepository;
        }

        public IActionResult GetAccounts() 
        {
            var usernameClaim = User.Claims.First(x => x.Type == "username");
            string username = usernameClaim.Value;
            IList<Account> accounts = bankingDataRepository.GetAccounts(username);
            return Ok(new AccountsResponse { Accounts = accounts });
        }
    }
}
