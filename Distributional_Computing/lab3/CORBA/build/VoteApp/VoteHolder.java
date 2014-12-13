package VoteApp;

/**
* VoteApp/VoteHolder.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从Vote.idl
* 2014年12月13日 星期六 下午05时00分12秒 CST
*/

public final class VoteHolder implements org.omg.CORBA.portable.Streamable
{
  public VoteApp.Vote value = null;

  public VoteHolder ()
  {
  }

  public VoteHolder (VoteApp.Vote initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = VoteApp.VoteHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    VoteApp.VoteHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return VoteApp.VoteHelper.type ();
  }

}
