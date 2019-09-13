using System;
namespace MoneyBankServer.Services
{
    public interface ILoginRepository
    {
        Login GetLogin(string username, string password);
    }
}
