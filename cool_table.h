#pragma once
#include <iostream>
#include <iomanip>
#include <string>
#include <vector>

void draw_table(std::ostream &out, const std::vector<std::string> &headers, const std::vector<std::vector<std::string>> &values,
	 const std::vector<int> &minWidth, const std::vector<int> &maxWidth)
{

	int colS = minWidth.size();	//Check input data

	if(colS != headers.size() || colS != maxWidth.size())
	{
		out << "Invalid input data to draw_table, not equal column number" << std::endl;
		return;
	}

	if(colS == 0)
        {
                out << "Invalid input data to draw_table, size == 0" << std::endl;
                return;
        }

	for(int i=0; i<values.size(); i++)
	{
		if(values[i].size() != colS)
		{
			out << "Invalid input data to draw_table, not equal" << std::endl;
			return;
		}
	}

//-----------------------------------------------

	std::vector<int> width = minWidth;	//count width of columns

	for(int i=0; i<headers.size(); i++)
	{
		if(headers[i].length() + 2 > width[i])
		{
			width[i] = headers[i].length() + 2;
		}
	}

	for(int i=0; i<values.size(); i++)
	{
		for(int j=0; j<values[i].size(); j++)
		{
			if(values[i][j].length() + 2 > width[j])
			{
				width[j] = values[i][j].length() + 2;
			}
		}
	}

	for(int i=0; i<maxWidth.size(); i++)
	{
		if(maxWidth[i] < width[i])
		{
			width[i] = maxWidth[i];
		}
	}

//-----------------------------------------------

	int commonWidth = headers.size() + 1; //create separator string
	for(int i=0; i<width.size(); i++)
	{
		commonWidth += width[i];
	}

	std::string separator(commonWidth, '-');
	separator[0] = '+';
	int curPos = 0;
	for(int i=0; i<width.size(); i++)
	{
		separator[curPos + width[i] + 1] = '+';
		curPos += width[i] + 1;
	}

//---------------------------------------------- 
		
	out << separator << std::endl;	//draw table
	
	out << '|';
	for(int i=0; i<headers.size(); i++)
	{
		out << ' ' << std::setw(width[i] - 2) << headers[i].substr(0, width[i] - 2) << " |";
	}
	out << std::endl;

	out << separator << std::endl;

	for(int i=0; i<values.size(); i++)
	{
		out << '|';
		for(int j=0; j<values[i].size(); j++)
		{
			out << ' ' << std::setw(width[j] - 2) << values[i][j].substr(0, width[j] - 2) << " |";
		}
		out << std::endl;
	}

	out << separator << std::endl;
}
 
