
/**
Author:
NAME:RAHUL PRASHANT SHAH
UNITY ID:200106475
**/
import java.util.*;
import java.io.*;
public class SearchRomania
{
	static String path="";
	static boolean dest_found; 
	static Set<String> vis = new HashSet<String>();
	static Map<String,String> parent=new HashMap<String,String>();
	static int top=-1;
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Map <String,Node> hm=new HashMap<String,Node>();
		while(true)
		{
			String s=br.readLine();
			if(s==null||s.trim().equals(""))
				break;
			String tup[]=s.substring(s.indexOf('(')+1,s.indexOf(')')).split(",");
			tup[0]=tup[0].trim();
			tup[1]=tup[1].trim();
			tup[2]=tup[2].trim();
			if(!hm.containsKey(tup[0]))
			{
				hm.put(tup[0],new Node(tup[0]));
			}
			if(!hm.containsKey(tup[1]))
			{
				hm.put(tup[1],new Node(tup[1]));
			}
			Node from=hm.get(tup[0]);
			Node to=hm.get(tup[1]);
			from.neighbors.add(new Edge(to,Integer.parseInt(tup[2])));
			to.neighbors.add(new Edge(from,Integer.parseInt(tup[2])));
		}
		for (String key : hm.keySet()) 
		{
    		Node node = hm.get(key);
    		Collections.sort(node.neighbors, new CustomComparator());
		}

		Node source=hm.get(args[1]);
		Node dest=hm.get(args[2]);
		String method=args[0]; 
		if(source==null)
		{
			System.out.println("Source City not found");
		}
		else if(dest==null)
		{
			System.out.println("Destination City not found");	
		}
		else if(method.toLowerCase().equals("dfs"))
		{
			dfs(source,dest);
			System.out.println("Nodes expanded:\n"+path);
		}
		else if(method.toLowerCase().equals("bfs"))
		{
			bfs(source,dest);
			System.out.println("Nodes expanded:\n"+path);
		}
		else
		{
			System.out.println("Format is java SearchRomania.java search_method source destination < input.txt");
		}
	}
	static void dfs(Node source,Node dest)
	{
		Node st[] = new Node[100];
		st[++top]=source;
		path = path + source.name + ",";
		vis.clear();
		while(!(top==-1))
		{
			Node temp=st[top];
			vis.add(temp.name);
			
			int i;
			int sz=temp.neighbors.size();
			for(i=0;i<sz;i++)
			{
				Edge t2=temp.neighbors.get(i);
				if(!vis.contains(t2.node.name))
				{
					st[++top]=(t2.node);
					if(t2.node.name.equals(dest.name))
					{
						path = path+ t2.node.name;
						dest_found=true;
						String ans="";
						for(int j=0;j<=top;j++)
						{

							if(j!=top)
								ans=ans+st[j].name+"->";
							else
								ans=ans+st[j].name;
						}
						System.out.println("Solution path:\n"+ans+"\nLength:\n"+(top+1));
					}
					else if(!dest_found)
					{
						path = path + t2.node.name + ",";
					}
					break;
				}
			}
			if(i==sz)
				st[top--]=null;
		}
	}

	static void bfs(Node node,Node dest)
	{
		Queue<Node> q=new LinkedList<Node>();
		q.add(node);
		vis.clear();
		vis.add(node.name);
		while(!q.isEmpty())
		{
			Node temp=q.peek();
			if(temp.name.equals(dest.name))
			{
				path = path + temp.name;
				dest_found=true;
			}
			else if(!dest_found)
			{
				path = path + temp.name + ",";
			}
			if(temp.name.equals(dest.name))
			{
				int cnt=0;
				String s = "";
				String ss=temp.name;
				while(parent.get(ss)!=null)
				{
					s+=ss;
					s+=",";
					ss=parent.get(ss);
					cnt++;
				}
				s+=ss;
				cnt++;
				String sss[]=s.split(",");
				String ans="";
				for(int i=sss.length-1;i>=0;i--)
				{
					if(i!=0)
						ans=ans+sss[i]+"->";
					else
						ans=ans+sss[i];	
				}
				System.out.println("Solution path:\n"+ans+"\nLength:\n"+cnt);
			}
			int sz=temp.neighbors.size();
			for(int i=0;i<sz;i++)
			{
				Edge t2=temp.neighbors.get(i);
				if(!vis.contains(t2.node.name))
				{
					q.add(t2.node);
					vis.add(t2.node.name);
					parent.put(t2.node.name,temp.name);
				}
			}
			q.remove();
		}
	}
}

class Edge
{
	int wt;
	Node node;
	Edge(Node node,int wt)
	{
		this.wt=wt;
		this.node=node;
	}
}
class CustomComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e1, Edge e2) {
        return e1.node.name.compareTo(e2.node.name);
    }
}
class Node
{
	List<Edge> neighbors;
	String name;

	Node(String name)
	{
		this.name=name;
		neighbors=new ArrayList<Edge>();
	}
}