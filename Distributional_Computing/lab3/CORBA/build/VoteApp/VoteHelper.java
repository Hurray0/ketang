package VoteApp;


/**
* VoteApp/VoteHelper.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从Vote.idl
* 2014年12月13日 星期六 下午05时00分12秒 CST
*/

abstract public class VoteHelper
{
  private static String  _id = "IDL:VoteApp/Vote:1.0";

  public static void insert (org.omg.CORBA.Any a, VoteApp.Vote that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static VoteApp.Vote extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (VoteApp.VoteHelper.id (), "Vote");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static VoteApp.Vote read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_VoteStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, VoteApp.Vote value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static VoteApp.Vote narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof VoteApp.Vote)
      return (VoteApp.Vote)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      VoteApp._VoteStub stub = new VoteApp._VoteStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static VoteApp.Vote unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof VoteApp.Vote)
      return (VoteApp.Vote)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      VoteApp._VoteStub stub = new VoteApp._VoteStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
