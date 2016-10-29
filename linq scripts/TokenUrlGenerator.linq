<Query Kind="Statements" />

string s = Console.ReadLine();

string[] sArr = s.Split(new string[] {"&", "="}, StringSplitOptions.None);

string code = sArr[1];

string url = "http://localhost:8080/redirect?code=" 
+ code + "&state=spr";

url.Dump();